import * as React from 'react';

interface ICircleLoadingProps {
	width?: number;
	height?: number;
	color?: string;
}

const CircleLoading: React.FunctionComponent<ICircleLoadingProps> = ({
	width = 6,
	height = 6,
	color = '#ffffff',
}) => {
	const style = {
		width: `${width}px`,
		height: `${height}px`,
		backgroundColor: color,
	};
	return (
		<div className="follow-the-leader">
			<div style={style}></div>
			<div style={style}></div>
			<div style={style}></div>
			<div style={style}></div>
			<div style={style}></div>
		</div>
	);
};

export default CircleLoading;
