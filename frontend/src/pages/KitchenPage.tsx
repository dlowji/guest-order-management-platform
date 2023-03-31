import { categoryKitchenOrder } from '@constants/categoryKitchenOrder';
import CategoriesHeader from '@modules/common/CategoriesHeader';
import LoadingCenter from '@modules/common/LoadingCenter';
import MainContentHeader from '@modules/common/MainContentHeader';
import KitchenMain from '@modules/kitchen/KitchenMain';
import { useQueryString } from '@utils/queryString';
import * as React from 'react';
import { useNavigate } from 'react-router-dom';

interface IKitchenPageProps {}

const KitchenPage: React.FunctionComponent<IKitchenPageProps> = () => {
	const { q: status } = useQueryString();
	const navigate = useNavigate();
	const [isLoading, setIsLoading] = React.useState(false);
	React.useEffect(() => {
		setIsLoading(true);
		if (!status) {
			navigate(`/kitchen/${categoryKitchenOrder[0].link}`);
		}
		setIsLoading(false);
	}, [status]);

	return (
		<div className="mt-3">
			<CategoriesHeader categories={categoryKitchenOrder}></CategoriesHeader>
			{!isLoading ? (
				<>
					<MainContentHeader title="Orders"></MainContentHeader>
					<KitchenMain></KitchenMain>
				</>
			) : (
				<LoadingCenter></LoadingCenter>
			)}
		</div>
	);
};

export default KitchenPage;
