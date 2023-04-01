import MainContentHeader from '@modules/common/MainContentHeader';
import HistoryMain from '@modules/history/HistoryMain';
import * as React from 'react';
import Datepicker from 'tailwind-datepicker-react';
interface IHistoryPageProps {}
const options = {
	title: 'Choose from',
	autoHide: true,
	todayBtn: true,
	clearBtn: true,
	maxDate: new Date('2030-01-01'),
	minDate: new Date('1950-01-01'),
	theme: {
		background: 'bg-gray-700 dark:bg-gray-800',
		todayBtn: 'bg-orange-500 hover:bg-orange-600',
		clearBtn: '',
		icons: '',
		text: '',
		disabledText: '',
		input: '',
		inputIcon: '',
		selected: '',
	},
	icons: {
		// () => ReactElement | JSX.Element
		prev: () => (
			<span>
				<i className="fa fa-angle-left"></i>
			</span>
		),
		next: () => (
			<span>
				<i className="fa fa-angle-right"></i>
			</span>
		),
	},
	datepickerClassNames: 'right-0 top-15',
	defaultDate: new Date(),
	language: 'en',
};
const HistoryPage: React.FunctionComponent<IHistoryPageProps> = () => {
	const [show, setShow] = React.useState<boolean>(false);
	const [selectedDate, setSelectedDate] = React.useState<Date | null>(null);
	const [filter, setFilter] = React.useState<'day' | 'month' | 'year'>('day');
	const handleChange = (selectedDate: Date) => {
		setSelectedDate(selectedDate);
	};
	const handleClose = (state: boolean) => {
		setShow(state);
	};

	const handleFilterChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
		setFilter(e.target.value as 'day' | 'month' | 'year');
	};

	return (
		<div className="mt-3">
			<>
				<MainContentHeader title="History">
					<div className="flex items-center justify-end gap-3 p-4 rounded-lg border-2 border-grayeb">
						<div className="flex items-center gap-2">
							<select
								id="countries"
								className="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg  block w-full p-2.5 outline-none"
								onChange={handleFilterChange}
								value={filter}
							>
								<option value="day">Day</option>
								<option value="month">Month</option>
								<option value="year">Year</option>
							</select>
						</div>
						<div className="flex items-center gap-2  mr-5 relative">
							<Datepicker
								options={options}
								onChange={handleChange}
								show={show}
								setShow={handleClose}
							/>
						</div>
					</div>
				</MainContentHeader>

				<HistoryMain selectedDate={selectedDate} filter={filter}></HistoryMain>
			</>
		</div>
	);
};

export default HistoryPage;
