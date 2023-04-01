import orderApi from '@api/order';
import MainContentHeader from '@modules/common/MainContentHeader';
import StatisticItem from '@modules/common/StatisticItem';
import StockOutTable from '@modules/dashboard/StockOutTable';
import TrendingTable from '@modules/dashboard/TrendingTable';
import { useQuery } from '@tanstack/react-query';
import * as React from 'react';

interface IDashboardPageProps {}

const DashboardPage: React.FunctionComponent<IDashboardPageProps> = () => {
	const { data: statisticItems } = useQuery({
		queryKey: ['statisticItemsDashboard'],
		queryFn: () => {
			return orderApi.getDashboardStatistics();
		},
	});

	return (
		<div className="mb-10">
			<MainContentHeader
				title="Palmon Restaurant"
				quantity={new Date().toDateString()}
			></MainContentHeader>

			<div className="mt-3 grid xl:grid-cols-4 md:grid-cols-2 grid-cols-1 gap-5">
				{statisticItems?.data &&
					statisticItems?.data.map((item, index) => {
						return (
							<StatisticItem
								image={item.image}
								title={item.title}
								value={item.value}
								key={index}
							></StatisticItem>
						);
					})}
			</div>

			<div className="mt-10 grid xl:grid-cols-2 gap-5">
				<TrendingTable></TrendingTable>
				<StockOutTable></StockOutTable>
			</div>
		</div>
	);
};

export default DashboardPage;
