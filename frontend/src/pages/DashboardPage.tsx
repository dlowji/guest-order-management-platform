import Role from '@constants/ERole';
import DashboardSidebar from '@modules/dashboard/DashboardSidebar';
import { useAuth } from '@stores/useAuth';
import * as React from 'react';
import { Outlet, useNavigate } from 'react-router-dom';

interface IDashboardPageProps {}

const DashboardPage: React.FunctionComponent<IDashboardPageProps> = (props) => {
	const user = useAuth((state) => state.user);

	const navigate = useNavigate();

	React.useEffect(() => {
		if (!user) {
			navigate('/login');
			return;
		}
		const userRole = user.roleName;
		if (userRole && userRole === Role.CHEF) {
			navigate('/menu');
			return;
		}
		if (userRole && userRole === Role.EMPLOYEE) {
			navigate('/table');
			return;
		}
	}, [user]);
	return <></>;
};

export default DashboardPage;
