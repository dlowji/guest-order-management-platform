import CircleLoading from '@components/loading/CircleLoading';
import * as React from 'react';
import { Link } from 'react-router-dom';

interface IButtonProps {
	type: 'submit' | 'button';
	className?: string;
	children: React.ReactNode;
	onClick?: () => void;
	href?: string;
	isLoading?: boolean;
	icon?: React.ReactNode;
	variant?: 'primary' | 'secondary';
}
const Button: React.FunctionComponent<IButtonProps> = ({
	type,
	className = '',
	children,
	onClick,
	href,
	isLoading = false,
	icon = <CircleLoading />,
	variant,
}) => {
	const child = isLoading ? icon : children;

	if (variant === 'secondary') {
		className += 'bg-gray-200 text-gray-700 hover:bg-gray-300 focus:bg-gray-300 active:bg-gray-400';
	}

	if (href) {
		return (
			<Link
				to={href}
				className={`inline-block px-7 py-3 font-semibold text-sm leading-snug uppercase rounded shadow-md transition duration-150 ease-in-out ${className}`}
				onClick={onClick}
			>
				{child}
			</Link>
		);
	}
	return (
		<button
			type={type}
			className={`inline-block px-7 py-3 bg-orange-400 font-semibold text-sm leading-snug uppercase rounded shadow-md hover:bg-primaryff hover:shadow-lg focus:bg-primaryff focus:shadow-lg focus:outline-none focus:ring-0 active:bg-orange-700 active:shadow-lg transition duration-150 ease-in-out ${className}`}
			onClick={onClick}
		>
			{child}
		</button>
	);
};

export default Button;
