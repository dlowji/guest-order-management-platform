import * as React from 'react';

interface ITableListProps {
	children?: React.ReactNode;
}

const TableList: React.FunctionComponent<ITableListProps> = ({ children }) => {
	return <div className="table-list">{children}</div>;
};

export default TableList;
