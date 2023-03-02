import * as React from 'react';

interface IDotsWaveLoadingProps {
	width?: number;
	height?: number;
	color?: string;
}

const DotsWaveLoading: React.FunctionComponent<IDotsWaveLoadingProps> = (props) => {
	const { width = 20, height = 20, color } = props;

	return (
		<div className="bounce-loading">
			<div
				style={{
					width: `${width}px`,
					height: `${height}px`,
					background: color,
				}}
			></div>
			<div
				style={{
					width: `${width}px`,
					height: `${height}px`,
					background: color,
				}}
			></div>
			<div
				style={{
					width: `${width}px`,
					height: `${height}px`,
					background: color,
				}}
			></div>
			<div
				style={{
					width: `${width}px`,
					height: `${height}px`,
					background: color,
				}}
			></div>
		</div>
	);
};

export default DotsWaveLoading;
