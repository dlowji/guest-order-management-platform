import * as React from 'react';
import { NavLink } from 'react-router-dom';
import { toast } from 'react-toastify';
interface IDashboardSidebarProps {}

const items = [
	{ name: 'Home', href: '/', icon: 'fa-solid fa-house', isActive: false },
	{ name: 'Table', href: '/table', icon: 'fa-solid fa-table', isActive: true },
	{ name: 'Menu', href: '/menu', icon: 'fa-sharp fa-solid fa-bell-concierge', isActive: true },
	{ name: 'Order', href: '/order', icon: 'fa-solid fa-cart-shopping', isActive: true },
	{ name: 'History', href: '/history', icon: 'fa-solid fa-clock', isActive: false },
	{ name: 'Report', href: '/report', icon: 'fa-solid fa-chart-simple', isActive: false },
];

const DashboardSidebar: React.FunctionComponent<IDashboardSidebarProps> = () => {
	const handleItemInActive = (id: string) => {
		toast.error('This feature is not available yet', {
			autoClose: 2000,
			toastId: id,
		});
	};
	return (
		<div className="sidebar">
			<div className="sidebar-logo">
				<img srcSet="/images/logo.png 2x" alt="logo" />
				<span className="sidebar-title">Hotpot</span>
			</div>
			<ul className="sidebar-list">
				{items.map((item) => (
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
			<div className="sidebar-profile">
				<div className="sidebar-profile-container">
					<img srcSet="/images/profile.jpg 2x" alt="profile" />
					<span className="sidebar-profile-name">Admin</span>
				</div>
			</div>
		</div>
	);
};

export default DashboardSidebar;
