import CircleLoading from '@components/loading/CircleLoading';
import * as React from 'react';

interface ILoadingCenterProps {
	className?: string;
	color?: string;
}

const LoadingCenter: React.FunctionComponent<ILoadingCenterProps> = ({
	className = '',
	color = '#ff7200',
}) => {
	return (
		<div className={`flex items-center justify-center w-full ${className}`}>
			<CircleLoading color={color}></CircleLoading>
		</div>
	);
};

export default LoadingCenter;
