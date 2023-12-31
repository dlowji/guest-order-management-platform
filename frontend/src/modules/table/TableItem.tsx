import { TStatusTable } from '@customTypes/index';
import * as React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { toast } from 'react-toastify';
import { useAuth } from '@stores/useAuth';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import orderApi from '@api/order';
interface ITableItem {
	seats: number;
	title: string;
	status: TStatusTable;
	updatedAt: string;
	id: string;
}

interface ITableItemProps {
	item: ITableItem;
}

const TableItem: React.FunctionComponent<ITableItemProps> = ({
	item: { seats = 2, title = 'Table 1', status = 'FREE', id: tableId, updatedAt = Date.now() },
}) => {
	if (seats % 2 !== 0) seats += 1;
	const currentUser = useAuth((store) => store.user);

	const statusColor = React.useMemo(() => {
		if (status === 'FREE') return 'table-item-free';
		if (status === 'OCCUPIED') return 'table-item-dineIn';
		if (status === 'CHECK_IN') return 'table-item-ordered';
		return '';
	}, [status]);
	const navigate = useNavigate();
	const queryClient = useQueryClient();
	const { mutate: createOrder } = useMutation({
		mutationFn: (employeeId: string) => orderApi.placeTableOrder(tableId, employeeId),
		onSuccess: (data) => {
			if (data.code === 200) {
				navigate(`/menu/order/${data.orderId}`);
			} else {
				toast.error(data.message);
			}
		},
		onError: () => {
			toast.error("Can't place order");
		},
	});

	const { mutate: chooseTableOccupied } = useMutation({
		mutationFn: () => orderApi.getByTableId(tableId),
		onSuccess: (data) => {
			if (data.code === 200) {
				navigate(`/menu/order/${data.data?.orderId}`);
			} else {
				toast.error(data.message);
			}
		},
		onError: () => {
			toast.error('This table is occupied, please choose another table');
		},
	});

	const handleChooseTable = async () => {
		const employeeId = currentUser?.accountId;
		if (!employeeId) {
			toast.error('You are not logged in');
			return null;
		}
		if (status === 'FREE') {
			queryClient.invalidateQueries(['table', '']);
			Swal.fire({
				title: 'Choose table',
				text: `Do you want to choose this ${title}?`,
				icon: 'question',
				cancelButtonText: 'No',
				confirmButtonText: 'Yes',
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				showCancelButton: true,
			}).then(async (result) => {
				if (result.isConfirmed) {
					createOrder(employeeId);
				}
			});
		}

		if (status === 'CHECK_IN') {
			toast.error('This table is ordered, please choose another table');
		}

		if (status === 'OCCUPIED') {
			chooseTableOccupied();
		}
	};
	return (
		<Link
			onClick={handleChooseTable}
			className={`cursor-pointer hover:opacity-90 hover:scale-95 transition-all duration-300 ease-out table-item table-item-${seats} ${statusColor} `}
			to={''}
			style={{
				maxWidth: 150 + (50 * seats) / 2 + 'px',
			}}
		>
			{Array.from({ length: seats / 2 }).map((_, index) => (
				<div
					className="table-seat"
					key={index * Math.random()}
					style={{
						maxWidth: 100 + (50 * seats) / 2 + 'px',
					}}
				></div>
			))}
			<div className="table-content">
				<div className="table-content__title">{title}</div>
				<div className="table-content__status">{status}</div>
				{status === 'CHECK_IN' ||
					(status === 'OCCUPIED' && (
						<div className="table-content__timeIn flex items-baseline gap-2">
							<i className="fa fa-clock"></i>
							<span>{new Date(updatedAt).toLocaleTimeString()}</span>
						</div>
					))}
			</div>
			{Array.from({ length: seats / 2 }).map((_, index) => (
				<div
					className="table-seat"
					key={index * Math.random()}
					style={{
						maxWidth: 100 + (50 * seats) / 2 + 'px',
					}}
				></div>
			))}
		</Link>
	);
};

export default TableItem;
