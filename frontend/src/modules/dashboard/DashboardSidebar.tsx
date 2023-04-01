import Role from '@constants/ERole';
import { DropdownProvider } from '@context/useDropdown';
import SubMenuSidebar from '@modules/common/SubMenuSidebar';
import { useAuth } from '@stores/useAuth';
import * as React from 'react';
import { NavLink } from 'react-router-dom';
import { toast } from 'react-toastify';
interface IDashboardSidebarProps {}

const items = [
	{
		name: 'Home',
		href: '/home',
		icon: 'fa-solid fa-house',
		isActive: true,
		permissions: [Role.ADMIN],
	},
	{
		name: 'Table',
		href: '/table',
		icon: 'fa-solid fa-table',
		isActive: true,
		permissions: [Role.ADMIN, Role.EMPLOYEE],
	},
	{
		name: 'Menu',
		href: '/menu',
		icon: 'fa-sharp fa-solid fa-bell-concierge',
		isActive: true,
		permissions: [Role.EMPLOYEE, Role.CHEF, Role.ADMIN],
	},
	{
		name: 'Order',
		href: '/order',
		icon: 'fa-solid fa-shopping-cart',
		isActive: true,
		permissions: [Role.ADMIN, Role.EMPLOYEE],
	},
	{
		name: 'Kitchen',
		href: '/kitchen',
		icon: 'fa-solid fa-utensils',
		isActive: true,
		permissions: [Role.CHEF],
	},
	{
		name: 'History',
		href: '/history',
		icon: 'fa-solid fa-clock',
		isActive: true,
		permissions: [Role.ADMIN],
	},
	{
		name: 'Report',
		href: '/report',
		icon: 'fa-solid fa-chart-simple',
		isActive: false,
		permissions: [Role.ADMIN],
	},
];

const DashboardSidebar: React.FunctionComponent<IDashboardSidebarProps> = () => {
	const handleItemInActive = (id: string) => {
		toast.error('This feature is not available yet', {
			autoClose: 2000,
			toastId: id,
		});
	};

	const [currentRole, setCurrentRole] = React.useState<Role>(Role.EMPLOYEE);

	const itemsFilter = React.useMemo(() => {
		return items.filter((item) => item.permissions.includes(currentRole));
	}, [currentRole]);

	const user = useAuth((state) => state.user);

	React.useEffect(() => {
		if (user?.roleName) {
			setCurrentRole(user.roleName);
		}
	}, [user?.roleName]);

	return (
		<div className="sidebar">
			<div className="sidebar-logo">
				<img srcSet="/images/logo.png 2x" alt="logo" />
				<span className="sidebar-title">Palmon</span>
			</div>
			<ul className="sidebar-list">
				{itemsFilter.map((item) => (
					<li className="sidebar-item" key={item.name}>
						{!item.isActive ? (
							<button className="sidebar-link" onClick={() => handleItemInActive(item.href)}>
								<i className={item.icon}></i>
								<span className="sidebar-name">{item.name}</span>
							</button>
						) : (
							<NavLink
								to={item.href}
								className={({ isActive }) => (isActive ? `sidebar-link active` : `sidebar-link`)}
							>
								<i className={item.icon}></i>
								<span className="sidebar-name">{item.name}</span>
							</NavLink>
						)}
					</li>
				))}
			</ul>
			<DropdownProvider>
				<SubMenuSidebar />
			</DropdownProvider>
		</div>
	);
};

export default DashboardSidebar;
