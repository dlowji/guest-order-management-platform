import { TStatusTable } from '@customTypes/index';
import * as React from 'react';

interface ITableContentItemProps {
	title: string;
	status: TStatusTable;
	quantity: number;
}

const TableContentItem: React.FunctionComponent<ITableContentItemProps> = ({
	title,
	status,
	quantity,
}) => {
	return (
		<div className="table-information__item">
			<div className="table-information__item__title">{title}</div>
			<div className="table-information__item__status">{status}</div>
			<div className="table-information__item__quantity">
				{status === 'FREE' ? 'FREE' : `${quantity} people`}
			</div>
		</div>
	);
};

export default TableContentItem;
