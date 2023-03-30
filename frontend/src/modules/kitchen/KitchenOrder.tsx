import orderApi from '@api/order';
import LoadingCenter from '@modules/common/LoadingCenter';
import Topnav from '@modules/common/Topnav';
import { useQuery } from '@tanstack/react-query';
import * as React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import KitchenOrderLineItems from './KitchenOrderLineItems';
import { useOrderDetail } from '@context/useOrderDetail';

interface IKitchenOrderProps {}

const KitchenOrder: React.FunctionComponent<IKitchenOrderProps> = () => {
	const { orderId } = useParams();
	const { setOrderDetail } = useOrderDetail();

	const { data: orderDetail, isFetching } = useQuery({
		queryKey: ['order', orderId],
		queryFn: () => {
			return orderApi.getById(orderId);
		},
		onSuccess: (data) => {
			if (data.code === 200 && data?.data) {
				setOrderDetail(data?.data);
			}
		},
	});

	const navigate = useNavigate();

	const handleBackToPreviousPage = () => {
		navigate(-1);
	};

	if (isFetching) {
		return <LoadingCenter className="mt-10"></LoadingCenter>;
	}
	return (
		<>
			<Topnav
				titleMain={`Order ${orderId?.slice(-4)}`}
				onBack={handleBackToPreviousPage}
				onBackToHome={() => navigate('/')}
				subTitle={orderDetail?.data?.tableName || ''}
				canBack={true}
				canBackToHome={false}
			></Topnav>
			<div className="mt-10">
				<KitchenOrderLineItems
					items={orderDetail?.data?.orderLineItemResponseList || []}
				></KitchenOrderLineItems>
			</div>
		</>
	);
};

export default KitchenOrder;
