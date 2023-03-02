import * as React from 'react';
import { useController } from 'react-hook-form';
import { IInputProps } from './interface';

const Input: React.FunctionComponent<IInputProps> = ({
	id,
	control,
	name,
	type = 'text',
	placeholder,
	children,
}) => {
	const {
		field,
		fieldState: { error },
	} = useController({
		name,
		control,
		rules: { required: true },
		defaultValue: '',
	});
	return (
		<>
			<div className="relative">
				<input
					id={id !== undefined ? id : name}
					type={type}
					className={` form-control block w-full px-4 py-2 text-xl font-normal text-gray-700 bg-white bg-clip-padding border border-solid border-gray-300 rounded transition ease-in-out m-0 focus:text-gray-700 focus:bg-white focus:border-gray7b outline-none
          ${error?.message ? ' !border-error !text-error !focus:border-error' : ''}
        `}
					placeholder={!error?.message ? placeholder : ''}
					{...field}
				/>
				{children && (
					<span className="absolute cursor-pointer select-none right-6 top-2/4 -translate-y-2/4">
						{children}
					</span>
				)}
			</div>
			{error && error.message && (
				<span className="capitalize-first block text-sm font-semibold pointer-events-none text-error ">
					{error.message}
				</span>
			)}
		</>
	);
};

export default Input;
