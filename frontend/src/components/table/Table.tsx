import * as React from 'react';

interface ITableProps {
	children: React.ReactNode;
}

const Table: React.FunctionComponent<ITableProps> = (props) => {
	return <table className="w-full text-sm text-left text-gray-500">{props.children}</table>;
};

export default Table;
