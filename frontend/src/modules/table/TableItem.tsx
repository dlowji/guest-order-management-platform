import { TStatusTable } from '@customTypes/index';
import * as React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { toast } from 'react-toastify';
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
	item: { seats = 2, title = 'Table 1', status = 'FREE', id, updatedAt = Date.now() },
}) => {
	if (seats % 2 !== 0) seats += 1;
	const employeeId = `85e91e71-7ec2-454e-b0bd-f7cbeb5b588b`;
	const orderId = `8141e7c7-d663-47bc-b3b0-dd0beae5136b`;
	const statusColor = React.useMemo(() => {
		if (status === 'FREE') return 'table-item-free';
		if (status === 'OCCUPIED') return 'table-item-dineIn';
		if (status === 'CHECK_IN') return 'table-item-ordered';
		return '';
	}, [status]);
	const navigate = useNavigate();
	const handleChooseTable = async (id: string) => {
		if (status === 'FREE') {
			Swal.fire({
				title: 'Choose table',
				text: `Do you want to choose this ${title}?`,
				icon: 'question',
				cancelButtonText: 'No',
				confirmButtonText: 'Yes',
				confirmButtonColor: '#3085d6',
				cancelButtonColor: '#d33',
				showCancelButton: true,
			}).then((result) => {
				if (result.isConfirmed) {
					// navigate(`/menu/order/${id}`);
				}
			});
		}

		if (status === 'CHECK_IN') {
			toast.error('This table is ordered, please choose another table');
		}

		if (status === 'OCCUPIED') {
			toast.error('This table is occupied, please choose another table');
		}
	};
	return (
		<Link
			onClick={() => handleChooseTable(id)}
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
					(status === 'OCCUPIED' && <div className="table-content__timeIn"></div>)}
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
