import authApi from '@api/auth';
import CircleLoading from '@components/loading/CircleLoading';
import { TUser } from '@customTypes/index';
import { useAuth } from '@stores/useAuth';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import { getTokenService } from '@utils/localStorage';
import * as React from 'react';
import { Outlet, useLocation, useNavigate } from 'react-router-dom';

interface IAuthenticationLayoutProps {
	heading: string;
	subheading: string;
}

const AuthenticationLayout: React.FunctionComponent<IAuthenticationLayoutProps> = ({
	heading,
	subheading,
}) => {
	const token = getTokenService();
	const setUser = useAuth((state) => state.setUser);
	const navigate = useNavigate();
	const { isLoading, isFetching } = useQuery({
		queryKey: ['authUser', token],
		queryFn: () => authApi.getMe(),
		enabled: !!token,
		retry: 1,
		onSuccess: (data: TUser) => {
			console.log('🚀 ~ data:', data);
			setUser(data);
			navigate('/');
		},
		onError: (error) => {
			console.log(error);
		},
	});

	const loading = isLoading || isFetching;

	if (loading) {
		return (
			<div className="flex items-center justify-center w-full">
				<CircleLoading color="#ff7200"></CircleLoading>
			</div>
		);
	}

	return (
		<section className="h-screen mb-10">
			<div className="container px-6 py-12 h-full">
				<div className="flex justify-center items-center flex-wrap h-full text-gray-800">
					<div className="md:w-8/12 lg:w-5/12 mb-12 md:mb-0 ">
						<img src="/images/bg.png" className="aspect-square h-full" alt="Background" />
					</div>
					<div className="md:w-8/12 lg:w-6/12 lg:ml-20">
						<h1 className="text-4xl font-bold mb-4 text-center">{heading}</h1>
						<p className="text-gray-500 text-center mb-8">{subheading}</p>
						<Outlet></Outlet>
					</div>
				</div>
			</div>
		</section>
	);
};

export default AuthenticationLayout;
