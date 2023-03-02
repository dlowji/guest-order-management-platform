import TableContentOrder from '@modules/table/TableContentOrder';
import TableMain from '@modules/table/TableMain';
import * as React from 'react';

interface ITablePageProps {}

const TablePage: React.FunctionComponent<ITablePageProps> = () => {
	return (
		<section className="table">
			<TableMain></TableMain>
			<TableContentOrder></TableContentOrder>
		</section>
	);
};

export default TablePage;
