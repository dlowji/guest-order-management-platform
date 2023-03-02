import { useState } from 'react';

export default function useToggleValue(initialValue = false): {
	value: boolean;
	handleToggleValue: React.Dispatch<React.SetStateAction<boolean>>;
} {
	const [value, setValue] = useState<boolean>(initialValue);
	const handleToggleValue = () => {
		setValue(!value);
	};
	return {
		value,
		handleToggleValue,
	};
}
