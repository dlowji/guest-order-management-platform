import authApi from '@api/auth';
import DropdownList from '@components/dropdown/DropdownList';
import DropdownOption from '@components/dropdown/DropdownOption';
import { useDropdown } from '@context/useDropdown';
import useClickOutside from '@hooks/useClickOutside';
import { useAuth } from '@stores/useAuth';
import { useMutation } from '@tanstack/react-query';
import { removeTokenService } from '@utils/localStorage';
import * as React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

interface ISubMenuSidebarProps {}

const SubMenuSidebar: React.FunctionComponent<ISubMenuSidebarProps> = () => {
	const { handleToggleDropdown, setShow } = useDropdown();
	const navigate = useNavigate();
	const refDropdown = React.useRef<HTMLAnchorElement>(null);
	const removeUser = useAuth((state) => state.removeUser);
	useClickOutside([refDropdown], () => setShow(false));
	const { mutate: signOut } = useMutation({
		mutationFn: async () => {
			return await authApi.logout();
		},
		onSuccess: (data) => {
			if (data.status === 200) {
				Swal.fire({
					title: 'Success!',
					text: 'You have ended this shift. See you again!',
					icon: 'success',
					confirmButtonText: 'Ok',
				}).then(() => {
					removeUser();
					removeTokenService();
					navigate('/login');
				});
			}
		},
	});

	const handleSignOut = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Do you want to end this shift?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, end shift!',
		}).then((result) => {
			if (result.isConfirmed) {
				signOut();
			}
		});
	};

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
				<DropdownOption onClick={handleSignOut}>
					<span>Log out</span>
					<i className="fa fa-sign-out"></i>
				</DropdownOption>
			</DropdownList>
		</Link>
	);
};

export default SubMenuSidebar;
