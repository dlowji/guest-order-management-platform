import Button from '@components/button/Button';
import Caption from '@components/table/Caption';
import Table from '@components/table/Table';
import TBody from '@components/table/TBody';
import THead from '@components/table/THead';
import { IOrderDetails } from '@customTypes/index';
import TableHeaderSection from '@modules/common/TableHeaderSection';
import { calculateDuration } from '@utils/calculateDuration';
import { formatCurrency } from '@utils/formatCurrency';
import * as React from 'react';

interface IHistoryTableProps {
	items: IOrderDetails[];
	caption: string;
}

const hitoryTableHeader = [
	{
		id: 1,
		name: 'Order no',
	},
	{
		id: 2,
		name: 'Order by',
	},
	{
		id: 3,
		name: 'Grand total',
	},
	{
		id: 4,
		name: 'Duration',
	},
];

const HistoryTable: React.FunctionComponent<IHistoryTableProps> = ({
	items = [],
	caption = 'Order completed',
}) => {
	return (
		<Table>
			<Caption className="p-5 text-lg font-semibold text-left text-gray-900 bg-white">
				<TableHeaderSection header={caption} totalItems={items.length}></TableHeaderSection>
			</Caption>
			<THead headers={hitoryTableHeader} hasAction />
			<TBody>
				{items.length === 0 && (
					<tr className="bg-white border-b">
						<td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap ">
							No orders available
						</td>
					</tr>
				)}
				{items.map((item, index) => {
					const duration = calculateDuration(item.createdAt);
					const formatedDuration = `${duration.hours}h ${duration.minutes}m`;

					return (
						<tr key={index} className="bg-white border-b">
							<td className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap ">
								{item.orderId}
							</td>
							<td className="px-6 py-4">{item.accountName}</td>
							<td className="px-6 py-4">{formatCurrency(item.grandTotal)}</td>
							<td className="px-6 py-4">{formatedDuration}</td>
							<td className="px-6 py-4 flex items-center gap-3">
								<Button
									type="button"
									variant="primary"
									className="font-medium text-white"
									href={`/history/${item.orderId}`}
								>
									View
								</Button>
							</td>
						</tr>
					);
				})}
			</TBody>
		</Table>
	);
};

export default HistoryTable;
