import * as React from 'react';
import { IInputProps } from './interface';
import { useController } from 'react-hook-form';

type ICheckboxProps = Omit<IInputProps, 'type' | 'placeholder'>;

const Checkbox: React.FunctionComponent<ICheckboxProps> = ({ id, name, control }) => {
	const {
		field,
		fieldState: { isTouched },
	} = useController({
		name,
		control,
		defaultValue: false,
	});
	console.log(field.value);

	const isChecked = React.useMemo(() => {
		return field.value;
	}, [field.value]);

	return (
		<>
			<div className="flex items-start gap-x-5">
				<label
					htmlFor={id !== undefined ? id : name}
					className={`inline-flex items-center justify-center p-1 text-white w-5 h-5 border rounded cursor-pointer ${
						isChecked ? 'bg-primaryff border-primaryff' : 'border-strock dark:border-text3'
					}`}
				>
					<input id={id !== undefined ? id : name} type="checkbox" className="hidden" {...field} />
					<span className={`${isChecked ? '' : 'opacity-0 invisible'}`}>
						<svg
							xmlns="http://www.w3.org/2000/svg"
							className="w-5 h-5"
							viewBox="0 0 20 20"
							fill="currentColor"
						>
							<path
								fillRule="evenodd"
								d="M16.707 5.293a1 1 0 010 1.414l-8 8a1 1 0 01-1.414 0l-4-4a1 1 0 011.414-1.414L8 12.586l7.293-7.293a1 1 0 011.414 0z"
								clipRule="evenodd"
							/>
						</svg>
					</span>
				</label>
			</div>
		</>
	);
};

export default Checkbox;
