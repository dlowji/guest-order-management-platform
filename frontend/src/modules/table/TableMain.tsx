import MainContentHeader from '@modules/common/MainContentHeader';
import * as React from 'react';
import TableList from './TableList';
import TableItem from './TableItem';
import { TStatusTable } from '@customTypes/index';

interface ITableMainProps {}

const tableItem = [
	{
		id: 1,
		seats: 2,
		title: 'Table 1',
		status: 'available',
	},
	{
		id: 2,
		seats: 4,
		title: 'Table 2',
		status: 'occupied',
	},
	{
		id: 3,
		seats: 6,
		title: 'Table 3',
		status: 'ordered',
	},
	{
		id: 4,
		seats: 8,
		title: 'Table 4',
		status: 'available',
	},
	{
		id: 5,
		seats: 4,
		title: 'Table 5',
		status: 'available',
	},
	{
		id: 6,
		seats: 2,
		title: 'Table 6',
		status: 'available',
	},
];

const TableMain: React.FunctionComponent<ITableMainProps> = (props) => {
	return (
		<div className="table-left">
			<MainContentHeader title="Choose tables"></MainContentHeader>
			<TableList>
				{tableItem.map((item) => (
					<TableItem
						key={item.id}
						item={{
							id: item.id,
							seats: item.seats,
							title: item.title,
							status: item.status as TStatusTable,
						}}
					></TableItem>
				))}
			</TableList>
		</div>
	);
};

export default TableMain;
