import orderApi from '@api/order';
import { IMenuOrderItem } from '@interfaces/index';
import OrderReceipt from '@modules/checkout/OrderReceipt';
import LoadingCenter from '@modules/common/LoadingCenter';
import { useQuery } from '@tanstack/react-query';
import { calculateDuration } from '@utils/calculateDuration';
import * as React from 'react';
import { useParams } from 'react-router-dom';

interface IHistoryDetailProps {}

const HistoryDetail: React.FunctionComponent<IHistoryDetailProps> = () => {
	const { orderId } = useParams();
	const [orderLineItems, setOrderLineItems] = React.useState<IMenuOrderItem[]>([]);
	console.log('🚀 ~ orderLineItems:', orderLineItems);
	const { data: order, isFetching } = useQuery({
		queryKey: ['order', orderId],
		queryFn: () => orderApi.getById(orderId),
		onSuccess: (data) => {
			if (data.code === 200) {
				setOrderLineItems(data.data?.orderLineItemResponseList || []);
			}
		},
	});
	console.log('🚀 ~ data:', order);

	if (!orderId) {
		return (
			<div className="mx-20 mt-5 flex items-center justify-between gap-10 flex-col">
				<div className="flex flex-col w-full justify-center items-center">Order not found</div>
			</div>
		);
	}

	const formatDuration = React.useMemo(() => {
		const createdAt = order?.data?.createdAt || '';
		console.log('🚀 ~ formatDuration ~ createdAt:', createdAt);
		const lastProcessing = order?.data?.lastProcessing || '';
		console.log('🚀 ~ formatDuration ~ lastProcessing:', lastProcessing);
		const { days, hours, minutes, seconds } = calculateDuration(createdAt, lastProcessing);
		const formatStr = `${days}d ${hours}h ${minutes}m ${seconds}s`;
		return formatStr;
	}, [order?.data?.createdAt, order?.data?.lastProcessing]);

	return (
		<div className="mx-20 mt-5 flex items-center justify-between gap-10 flex-col">
			<h2 className="text-center font-bold text-4xl">Order Detail</h2>

			<div className="flex flex-col w-full">
				<h4 className="font-bold text-2xl">Order info</h4>
				<div className="flex flex-col gap-3 mt-5">
					<div className="flex items-center justify-between">
						<span className="font-semibold">Order ID:</span>
						<span className="text-slate-500">{order?.data?.orderId.slice(-4)}</span>
					</div>
					<div className="flex items-center justify-between">
						<span className="font-semibold">Status:</span>
						<span className="text-slate-500">{order?.data?.orderStatus}</span>
					</div>
					<div className="flex items-center justify-between">
						<span className="font-semibold">Order date:</span>
						<span className="text-slate-500">
							{new Date(order?.data?.createdAt || '').toUTCString()}
						</span>
					</div>
					<div className="flex items-center justify-between">
						<span className="font-semibold">Duration:</span>
						<span className="text-slate-500">{formatDuration}</span>
					</div>
					<div className="flex items-center justify-between">
						<span className="font-semibold">Order By:</span>
						<span className="text-slate-500">{order?.data?.accountName}</span>
					</div>
				</div>
			</div>

			<div className="flex flex-col w-full">
				<h4 className="font-semibold text-2xl">Order line items</h4>
				{isFetching && <LoadingCenter></LoadingCenter>}
				<OrderReceipt
					orderItems={orderLineItems}
					orderId={orderId}
					discount={order?.data?.discount || 0}
					tax={order?.data?.tax}
				></OrderReceipt>
			</div>
		</div>
	);
};

export default HistoryDetail;
