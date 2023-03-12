import authApi from '@api/auth';
import CircleLoading from '@components/loading/CircleLoading';
import Role from '@constants/ERole';
import { TUser } from '@customTypes/index';
import { useAuth } from '@stores/useAuth';
import { useQuery } from '@tanstack/react-query';
import { getTokenService } from '@utils/localStorage';
import * as React from 'react';
import { Navigate, Outlet } from 'react-router-dom';

interface IProtectedRouteProps {
	allowedRoles: Role[];
}

const ProtectedRoute: React.FunctionComponent<IProtectedRouteProps> = ({ allowedRoles }) => {
	const token = getTokenService();
	const setUser = useAuth((state) => state.setUser);
	const {
		isLoading,
		isFetching,
		data: user,
	} = useQuery({
		queryKey: ['authUser', token],
		queryFn: () => authApi.getMe(),
		enabled: !!token,
		retry: 1,
		onSuccess: (data: TUser) => {
			console.log('🚀 ~ data:', data);
			setUser(data);
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
	return user && allowedRoles.includes(user?.roleName as Role) ? (
		<Outlet />
	) : (
		<Navigate to="/login" replace />
	);
};

export default ProtectedRoute;
