import * as React from 'react';

interface IBadgeProps {
	varient:
		| 'primary'
		| 'secondary'
		| 'success'
		| 'danger'
		| 'warning'
		| 'info'
		| 'light'
		| 'dark'
		| '';
	className?: string;
	content: string;
}

const Badge: React.FunctionComponent<IBadgeProps> = ({ varient, className = '', content = '' }) => {
	const classNameByVarient = React.useMemo(() => {
		switch (varient) {
			case 'primary':
				return 'border-primaryff text-white bg-primaryff border-primaryff';
			case 'secondary':
				return 'border-secondaryff text-white bg-secondaryff border-secondaryff';
			case 'success':
				return 'bg-green-100 text-black border-green-400';
			case 'danger':
				return 'bg-red-100 text-white border-red-400';
			case 'warning':
				return 'bg-yellow-100 text-white border-yellow-400';
			case 'info':
				return 'bg-blue-100 text-white border-blue-400';
			case 'light':
				return 'bg-gray-100 text-white border-gray-400';
			case 'dark':
				return 'bg-gray-800 text-white border-gray-400';
			default:
				return '';
		}
	}, [varient]);

	return (
		<span
			className={`text-xs font-medium mr-2 px-2.5 py-0.5 rounded border ${classNameByVarient} ${className}`}
		>
			{content}
		</span>
	);
};

export default Badge;
