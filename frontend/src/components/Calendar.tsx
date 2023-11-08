import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

const CalendarComponent: React.FC = () => {
  const [selectedDate, setSelectedDate] = useState<Date | Date[]>(new Date());

  const handleDateChange = (date: Date | Date[]) => {
    setSelectedDate(date);
  };

  return (
    <div>
      <Calendar
        // 오류로 인한 타입 형변환을 사용
        onChange={handleDateChange as any}
        value={selectedDate as any}
        formatDay={(_, date) => (date instanceof Date ? date.getDate().toString() : '')}
      />
    </div>
  );
};

export default CalendarComponent;
