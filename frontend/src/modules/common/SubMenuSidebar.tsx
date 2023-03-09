import Dropdown from '@components/dropdown/Dropdown';
import DropdownList from '@components/dropdown/DropdownList';
import DropdownOption from '@components/dropdown/DropdownOption';
import { useDropdown } from '@context/useDropdown';
import useClickOutside from '@hooks/useClickOutside';
import * as React from 'react';
import { Link } from 'react-router-dom';

interface ISubMenuSidebarProps {}

const SubMenuSidebar: React.FunctionComponent<ISubMenuSidebarProps> = (props) => {
	const { handleToggleDropdown, setShow } = useDropdown();
	const refDropdown = React.useRef<HTMLAnchorElement>(null);
	useClickOutside([refDropdown], () => setShow(false));

	return (
		<Link
			to={'#'}
			className="sidebar-profile flex items-center gap-10 relative"
			onClick={() => handleToggleDropdown()}
			ref={refDropdown}
		>
			<div className="sidebar-profile-container">
				<img srcSet="/images/profile.jpg 2x" alt="profile" />
				<span className="sidebar-profile-name">Admin</span>
			</div>
			<DropdownList>
				<DropdownOption>
					<span>Log out</span>
					<i className="fa fa-sign-out"></i>
				</DropdownOption>
			</DropdownList>
		</Link>
	);
};

export default SubMenuSidebar;
