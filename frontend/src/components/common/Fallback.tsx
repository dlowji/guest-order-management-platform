import DotsWaveLoading from '@components/loading/DotsWaveLoading';
import * as React from 'react';

interface IFallbackProps {}

const Fallback: React.FunctionComponent<IFallbackProps> = () => {
	return (
		<div className="flex items-center justify-center w-full h-screen">
			<DotsWaveLoading></DotsWaveLoading>
		</div>
	);
};

export default Fallback;
