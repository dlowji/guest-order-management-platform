import MainContentHeader from '@modules/common/MainContentHeader';
import * as React from 'react';
import TableList from './TableList';
import TableItem from './TableItem';
import { TStatusTable } from '@customTypes/index';
import { useQuery } from '@tanstack/react-query';
import tableApi from '@api/table';
import { ITableResponse } from '@interfaces/table';
import CategoriesHeader from '@modules/common/CategoriesHeader';
import { categoriesTableItems } from 'constants/categoryTableItem';
import { useQueryString } from '@utils/queryString';
import CircleLoading from '@components/loading/CircleLoading';
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
	const queryString: { q?: string } = useQueryString();
	const status = queryString.q ? queryString.q : '';

	const { error, data, isFetching } = useQuery({
		queryKey: ['table', status],
		queryFn: () => {
			return tableApi.getTables(status);
		},
	});

	return (
		<div className="table-left">
			<MainContentHeader
				title="Choose tables"
				quantity={data?.length ? `${data.length} tables` : '0 tables'}
			></MainContentHeader>
			<CategoriesHeader
				categories={categoriesTableItems}
				className="!mt-[10px] "
			></CategoriesHeader>
			<TableList>
				{isFetching && (
					<div className="flex items-center justify-center w-full">
						<CircleLoading color="#ff7200"></CircleLoading>
					</div>
				)}
				{data &&
					!isFetching &&
					!error &&
					data.map((item: ITableResponse) => (
						<TableItem
							key={item.tableId}
							item={{
								id: item.tableId,
								seats: item.capacity,
								title: item.code,
								status: item.tableStatus as TStatusTable,
							}}
						></TableItem>
					))}
			</TableList>
		</div>
	);
};

export default TableMain;
