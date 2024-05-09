import React from 'react';
import Select from 'react-select';

const generateTimeOptions = () => {
  const options = [];
  for (let hour = 0; hour < 24; hour++) {
    const formattedHour = hour.toString().padStart(2, '0');
    const timeLabel = `${formattedHour}:00`; // 분을 항상 00으로 고정
    options.push({ label: timeLabel, value: timeLabel });
  }
  return options;
};

const options = generateTimeOptions();

interface TimeSelectProps {
  value: string;
  onChange: (selectedTime: string) => void;
  label: string;
}

const TimeSelect: React.FC<TimeSelectProps> = ({ value, onChange, label }) => {
  return (
    <div style={{ margin: '10px 0' }}>
      <label>{label}</label>
      <Select
        options={options}
        value={{ label: value, value }}
        onChange={(selectedOption) => onChange(selectedOption?.value as string)}
      />
    </div>
  );
};

export default TimeSelect;
