import * as React from 'react';

import orderApi from '@api/order';
import { useQuery } from '@tanstack/react-query';
import { useQueryString } from '@utils/queryString';
import KitchenTable from './KitchenTable';
import LoadingCenter from '@modules/common/LoadingCenter';
import { categoryKitchenOrder } from '@constants/categoryKitchenOrder';

interface IKitchenMainProps {}
const KitchenMain: React.FunctionComponent<IKitchenMainProps> = () => {
	const { q: status } = useQueryString();
	const { data: orderItems, isFetching } = useQuery({
		queryKey: ['order', status],
		queryFn: () => {
			return orderApi.getAll(status);
		},
	});

	const category = React.useMemo(() => {
		return categoryKitchenOrder.find((item) => item.id === status);
	}, [status]);

	if (isFetching) {
		return <LoadingCenter></LoadingCenter>;
	}

	return (
		<div className="kitchen pb-10">
			<div className="relative overflow-x-auto shadow-md sm:rounded-lg xl:max-w-[1920px] xl:mx-10 mx-auto mt-10">
				<KitchenTable items={orderItems?.data || []} caption={category?.name || ''}></KitchenTable>
			</div>
		</div>
	);
};

export default KitchenMain;
