import * as React from 'react';

interface ITableHeader {
	id: string | number;
	name: string;
}

interface ITHeadProps {
	headers: ITableHeader[];
	colClassName?: string;
	children?: React.ReactNode;
	hasAction?: boolean;
}

const THead: React.FunctionComponent<ITHeadProps> = ({
	headers,
	colClassName = '',
	children,
	hasAction = true,
}) => {
	return (
		<thead className="text-sm text-black uppercase bg-gray-100">
			<tr>
				{headers.map((header) => {
					return (
						<th key={header.id} className={`px-6 py-3 font-bold ${colClassName}`}>
							{header.name}
						</th>
					);
				})}
				{hasAction && <th className="px-6 py-3 font-bold">Action</th>}
				{children}
			</tr>
		</thead>
	);
};

export default THead;
