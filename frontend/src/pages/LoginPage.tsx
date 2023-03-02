import FormGroup from '@components/common/FormGroup';
import Input from '@components/input/Input';
import Label from '@components/label/Label';
import * as React from 'react';
import { FieldValues, useForm } from 'react-hook-form';
import * as yup from 'yup';
import { yupResolver } from '@hookform/resolvers/yup';
import useToggleValue from '@hooks/useToggleValue';
import Checkbox from '@components/input/Checkbox';
import { Link } from 'react-router-dom';
import IconEyeToggle from '@components/icon/IconEyeToggle';
import Button from '@components/button/Button';
import CircleLoading from '@components/loading/CircleLoading';

interface ILoginPageProps {}

const schema = yup.object().shape({
	email: yup.string().email().required(),
	password: yup.string().required(),
	remember: yup.boolean(),
});

const LoginPage: React.FunctionComponent<ILoginPageProps> = () => {
	const {
		handleSubmit,
		control,
		formState: { isSubmitting, isValid },
	} = useForm({
		resolver: yupResolver(schema),
		mode: 'onSubmit',
	});
	const { value: showPassword, handleToggleValue: handleTogglePassword } = useToggleValue();
	const handleSignIn = (values: FieldValues) => {
		if (isSubmitting || !isValid) return;
		console.log(values);
	};

	return (
		<form onSubmit={handleSubmit(handleSignIn)}>
			<FormGroup>
				<Label htmlFor="email">Email</Label>
				<Input
					name="email"
					id="email"
					control={control}
					type="text"
					placeholder="Email your address here"
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

			<div className="flex justify-between items-center mb-6">
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
			</FormGroup>

			<Button type="submit" className="w-full text-white" isLoading={isSubmitting}>
				Sign in
			</Button>
		</form>
	);
};

export default LoginPage;
