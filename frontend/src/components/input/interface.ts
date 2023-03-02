import { Control, FieldValues } from 'react-hook-form';

export interface IInputProps {
	id?: string;
	name: string;
	type: string;
	placeholder: string;
	control: Control<FieldValues>;
	children?: React.ReactNode;
}
