import DashboardSidebar from '@modules/dashboard/DashboardSidebar';
import * as React from 'react';
import { Outlet } from 'react-router-dom';

interface IMainLayoutProps {}

const MainLayout: React.FunctionComponent<IMainLayoutProps> = (props) => {
	return (
		<div className="menu-dashboard gap-4">
			<DashboardSidebar></DashboardSidebar>
			<div className="dashboard-content flex flex-1 mx-[20px] flex-col">
				<Outlet />
			</div>
		</div>
	);
};

export default MainLayout;
