import * as React from 'react';
import FAKE_ITEM from 'data/tableContentItems.json';
import TableContentItem from './TableContentItem';
import { TStatusTable } from '@customTypes/index';
import CategoriesHeader from '@modules/common/CategoriesHeader';

interface ITableContentOrderProps {}

interface ITableOrderItem {
	id: number;
	title: string;
	status: TStatusTable | string;
	quantity: number;
}

type TTableOrderState = {
	[key: string]: ITableOrderItem[];
};

const categoriesItem = [
	{
		id: 1,
		name: 'All',
		link: '/table',
	},
	{
		id: 2,
		name: 'Available',
		link: '/table?q=available',
	},
	{
		id: 3,
		name: 'Checked-in',
		link: '/table?q=checked-in',
	},
	{
		id: 4,
		name: 'Dine-in',
		link: '/table?q=dine-in',
	},
];

const TableContentOrder: React.FunctionComponent<ITableContentOrderProps> = () => {
	const [items, setItems] = React.useState<TTableOrderState>(FAKE_ITEM);

	return (
		<div className="table-right">
			<div className="table-right-container">
				<CategoriesHeader categories={categoriesItem}></CategoriesHeader>

				<div className="table-information">
					{Object.keys(items).map((key) => {
						return (
							<div className="table-information__list" key={key}>
								<div className="table-information__time">{key}</div>
								{Array.isArray(items[key]) &&
									items[key].map((item) => {
										return (
											<TableContentItem
												key={item.id}
												title={item.title}
												status={item.status as TStatusTable}
												quantity={item.quantity}
											></TableContentItem>
										);
									})}
							</div>
						);
					})}
				</div>
			</div>
		</div>
	);
};

export default TableContentOrder;
