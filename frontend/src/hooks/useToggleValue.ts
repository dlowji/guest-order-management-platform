import { useState } from 'react';

export default function useToggleValue(initialValue = false): {
	value: boolean;
	handleToggleValue: () => void;
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
