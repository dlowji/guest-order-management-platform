import * as React from 'react';
import TableStatistic from './TableStatistic';
import TrendingItem from './TrendingItem';
import orderApi from '@api/order';
import { useQuery } from '@tanstack/react-query';
import LoadingCenter from '@modules/common/LoadingCenter';

interface ITrendingTableProps {}

const TrendingTable: React.FunctionComponent<ITrendingTableProps> = () => {
	const { data: data, isFetching } = useQuery({
		queryKey: ['trendingItems'],
		queryFn: () => {
			return orderApi.getBestSeller(4);
		},
	});

	return (
		<TableStatistic title="Trending dishes" varient="trending">
			{isFetching && <LoadingCenter></LoadingCenter>}
			{data?.items?.length === 0 && (
				<div className="text-center text-gray-500 py-8">No trending dishes</div>
			)}
			{data?.items &&
				data?.items.map((item, index) => {
					return (
						<TrendingItem
							key={index}
							image={item.image}
							title={item.title}
							value={item.totalOrdered}
							price={item.price}
						></TrendingItem>
					);
				})}
		</TableStatistic>
	);
};

export default TrendingTable;
