import FormGroup from '@components/common/FormGroup';
import Input from '@components/input/Input';
import Label from '@components/label/Label';
import * as React from 'react';
import { FieldValues, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import useToggleValue from '@hooks/useToggleValue';
import IconEyeToggle from '@components/icon/IconEyeToggle';
import Button from '@components/button/Button';
import authApi from '@api/auth';
import { toast } from 'react-toastify';
import { setTokenService } from '@utils/localStorage';
import { useAuth } from '@stores/useAuth';
import { useMutation, useQuery } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

interface ILoginPageProps {}

const schema = yup.object().shape({
	username: yup.string().required(),
	password: yup.string().required(),
	// remember: yup.boolean(),
});

const LoginPage: React.FunctionComponent<ILoginPageProps> = () => {
	const {
		handleSubmit,
		control,
		formState: { isSubmitting, isValid },
		setError,
	} = useForm({
		resolver: yupResolver(schema),
		mode: 'onSubmit',
	});
	const { value: showPassword, handleToggleValue: handleTogglePassword } = useToggleValue();
	const setUser = useAuth((store) => store.setUser);
	const navigate = useNavigate();

	const { refetch } = useQuery(['authUser'], () => authApi.getMe(), {
		enabled: false,
		select: (data) => data,
		retry: 1,
		onSuccess: (data) => {
			setUser(data);
		},
	});

	const handleSignIn = async (values: FieldValues) => {
		if (isSubmitting || !isValid) return;

		const { username, password } = values;
		try {
			const { data, message, status } = await authApi.login(username, password);
			if (status === 200) {
				setTokenService(data as string);
				refetch();
				navigate('/table');
			} else {
				const errors = data as Record<string, string>;
				Object.keys(errors).forEach((key) => {
					setError(key as keyof FieldValues, {
						type: 'manual',
						message: errors[key],
					});
				});
				toast.error(message);
			}
		} catch (error) {
			toast.error("Can't login. Please try again later");
		}
	};

	return (
		<form onSubmit={handleSubmit(handleSignIn)}>
			<FormGroup>
				<Label htmlFor="Username">Username</Label>
				<Input
					name="username"
					id="username"
					control={control}
					type="text"
					placeholder="Username your address here"
				/>
			</FormGroup>

			<FormGroup>
				<Label htmlFor="password">Password</Label>
				<Input
					name="password"
					id="password"
					control={control}
					type={showPassword ? 'text' : 'password'}
					placeholder="Enter your password here"
				>
					<IconEyeToggle open={showPassword} onClick={handleTogglePassword}></IconEyeToggle>
				</Input>
			</FormGroup>

			{/* <div className="flex justify-between items-center mb-6">
				<FormGroup className="gap-3 items-center !flex-row !mb-0">
					<Checkbox name="remember" id="remember" control={control} />
					<Label htmlFor="remember">Remember me</Label>
				</FormGroup>
				<Link
					to="/"
					className=" hover:text-secondaryff focus:text-secondaryff active:text-secondaryff duration-200 transition ease-in-out text-primaryff"
				>
					Forgot password?
				</Link>
			</div>
			<FormGroup className="px-10 flex items-center flex-col gap-3">
				<span className="w-full border-dashed border"></span>
				<div className="flex items-center gap-2">
					<span className="text-gray-500">Dont have an account?</span>
					<Button
						type="button"
						href="/register"
						className=" hover:text-secondaryff focus:text-secondaryff active:text-secondaryff duration-200 transition ease-in-out text-primaryff"
						icon={<CircleLoading color="#ff7200" />}
						isLoading={isSubmitting}
					>
						Sign up
					</Button>
				</div>
			</FormGroup> */}

			<Button type="submit" className="w-full text-white" isLoading={isSubmitting}>
				Sign in
			</Button>
		</form>
	);
};

export default LoginPage;
