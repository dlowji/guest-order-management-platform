export const calculateDuration = (start: string, end?: string) => {
	if (start === '' && end === '') return { days: 0, hours: 0, minutes: 0, seconds: 0 };
	// Set the start time
	const startTime = new Date(start);

	// Get the current time
	const endTime = end ? new Date(end) : new Date();

	// Calculate the duration in milliseconds
	const durationMs = endTime.getTime() - startTime.getTime();

	// Convert milliseconds to days, hours, minutes, and seconds
	const durationDays = Math.floor(durationMs / (1000 * 60 * 60 * 24));
	const durationHours =
		Math.floor((durationMs % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)) + durationDays * 24;
	const durationMinutes = Math.floor((durationMs % (1000 * 60 * 60)) / (1000 * 60));
	const durationSeconds = Math.floor((durationMs % (1000 * 60)) / 1000);

	// Return the duration in days, hours, minutes, and seconds
	return {
		days: durationDays,
		hours: durationHours,
		minutes: durationMinutes,
		seconds: durationSeconds,
	};
};
