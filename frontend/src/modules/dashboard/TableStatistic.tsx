import * as React from 'react';

interface ITableStatisticProps {
	title: string;
	children: React.ReactNode;
	varient: 'trending' | 'stockout';
}

const TableStatistic: React.FunctionComponent<ITableStatisticProps> = ({
	title,
	children,
	varient,
}) => {
	return (
		<div className="p-5 bg-dashboardColor rounded-xl border">
			<h3
				className={`font-bold text-xl ${
					varient === 'trending' ? 'text-primaryff' : 'text-slate-600'
				}`}
			>
				{title}
			</h3>
			<div
				className={`mt-6 grid grid-cols-1 md:grid-cols-2 gap-5 max-h-[500px] overflow-y-auto pt-6 border-t border-dashed ${
					varient === 'trending' ? 'border-primaryff' : 'border-slate-600'
				}`}
			>
				{children}
			</div>
		</div>
	);
};

export default TableStatistic;
