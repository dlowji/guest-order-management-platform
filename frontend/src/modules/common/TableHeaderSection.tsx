import * as React from 'react';

interface IKitchenHeaderSectionProps {
	header: string;
	totalItems?: number;
}

const KitchenHeaderSection: React.FunctionComponent<IKitchenHeaderSectionProps> = ({
	header,
	totalItems = 0,
}) => {
	return (
		<div className="flex items-baseline gap-2 text-slate-500">
			<h2 className="text-2xl">{header}</h2>
			<span className="text-lg">{`(${totalItems})`}</span>
		</div>
	);
};

export default KitchenHeaderSection;
