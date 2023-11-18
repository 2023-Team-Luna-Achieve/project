import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

// 날짜 형식을 조절하여 출력하는 함수
const formatDate = (date: Date | Date[]): string => {
  if (date instanceof Date) {
    return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date
      .getDate()
      .toString()
      .padStart(2, '0')}`;
  } else {
    const startDate = date[0];
    return `${startDate.getFullYear()}-${(startDate.getMonth() + 1).toString().padStart(2, '0')}-${startDate
      .getDate()
      .toString()
      .padStart(2, '0')}`;
  }
};

const CalendarComponent: React.FC = () => {
  const [selectedDate, setSelectedDate] = useState<Date | Date[]>(new Date());

  const handleDateChange = (date: Date | Date[]) => {
    setSelectedDate(date);

    // 선택한 날짜를 콘솔에 출력
    const formattedDate = formatDate(date);
    console.log('Selected Date:', formattedDate);
  };

  return (
    <div>
      <Calendar
        onChange={handleDateChange as any}
        value={selectedDate as any}
        formatDay={(_, date) => (date instanceof Date ? date.getDate().toString() : '')}
      />
    </div>
  );
};

export default CalendarComponent;
