import kitchenApi from '@api/kitchen';
import Toggle from '@components/toggle/Toggle';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import * as React from 'react';
import Swal from 'sweetalert2';

interface IMenuButtonToggleProps {
	isAvailable: boolean;
	dishId: string;
}

const MenuButtonToggle: React.FunctionComponent<IMenuButtonToggleProps> = ({
	isAvailable = false,
	dishId,
}) => {
	const queryClient = useQueryClient();
	const { mutate: updateDishAvailability } = useMutation({
		mutationFn: () => {
			return kitchenApi.toggleDishStatus(dishId);
		},
		onSuccess: (data) => {
			if (data.code === 200) {
				queryClient.refetchQueries(['menuItems']);
				Swal.fire('Success!', 'Dish status has been updated.', 'success');
				return;
			} else {
				Swal.fire('Error!', data?.message || 'Error! Please try again later', 'error');
			}
		},
		onError: () => {
			Swal.fire('Error!', 'Something went wrong.', 'error');
		},
	});

	const handleToggle = () => {
		Swal.fire({
			title: 'Are you sure?',
			text: isAvailable ? 'Do you want to disable this dish?' : 'Do you want to enable this dish?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Yes, do it!',
		}).then((result) => {
			if (result.isConfirmed) {
				updateDishAvailability();
			}
		});
	};

	return <Toggle on={isAvailable} onClick={handleToggle}></Toggle>;
};

export default MenuButtonToggle;
