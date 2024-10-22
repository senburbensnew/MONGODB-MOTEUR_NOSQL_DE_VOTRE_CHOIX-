require 'json'
require 'csv'

def flatten_value(value)
  case value
  when Array
    value.join('; ')
  when Hash
    value.map { |k, v| "#{k}: #{v}" }.join('; ')
  else
    value
  end
end

def json_to_csv(json_file, csv_file)
  json_data = JSON.parse(File.read(json_file))

  CSV.open(csv_file, 'w') do |csv|
    headers = json_data.first.keys
    csv << headers
    
    json_data.each do |hash|
      csv << hash.values.map { |value| flatten_value(value) }
    end
  end
  puts "CSV file #{csv_file} has been generated successfully!"
end

json_to_csv('Medecin_By_Speciality.json', 'Medecin_By_Speciality.csv')

json_to_csv('Patient_By_BirthDay.json', 'Patient_By_BirthDay.csv')
