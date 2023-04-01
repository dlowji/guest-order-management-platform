import * as React from 'react';
import HistoryTable from './HistoryTable';
import { useQuery } from '@tanstack/react-query';
import orderApi from '@api/order';
import LoadingCenter from '@modules/common/LoadingCenter';

interface IHistoryMainProps {
	selectedDate: Date | null;
	filter: 'day' | 'month' | 'year';
}

const HistoryMain: React.FunctionComponent<IHistoryMainProps> = ({ selectedDate, filter }) => {
	const { data, isFetching } = useQuery({
		queryKey: ['history', selectedDate, filter],
		queryFn: () => {
			const dateObj = selectedDate ? new Date(selectedDate) : new Date();
			if (!selectedDate) {
				dateObj.setHours(0, 0, 0, 0);
			}

			const timeStamp = dateObj.getTime();
			return orderApi.getHistory(timeStamp / 1000, filter);
		},
	});

	if (isFetching) {
		return <LoadingCenter></LoadingCenter>;
	}

	return (
		<div className="history pb-10">
			<div className="relative overflow-x-auto shadow-md sm:rounded-lg xl:max-w-[1920px] xl:mx-10 mx-auto mt-10">
				<HistoryTable caption="Completed Orders" items={data?.data || []}></HistoryTable>
			</div>
		</div>
	);
};

export default HistoryMain;
