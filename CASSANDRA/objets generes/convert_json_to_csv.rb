require 'json'
require 'csv'

# Function to handle flattening nested structures like arrays and hashes
def flatten_value(value)
  case value
  when Array
    value.join('; ') # Join array elements with ';' for CSV
  when Hash
    value.map { |k, v| "#{k}: #{v}" }.join('; ') # Flatten hash into 'key: value' format
  else
    value
  end
end

# Function to convert JSON to CSV
def json_to_csv(json_file, csv_file)
  # Load the JSON data from the file
  json_data = JSON.parse(File.read(json_file))

  # Open a CSV file for writing
  CSV.open(csv_file, 'w') do |csv|
    # Extract column names (keys from the first JSON object)
    headers = json_data.first.keys
    csv << headers
    
    # Write each JSON object as a CSV row
    json_data.each do |hash|
      csv << hash.values.map { |value| flatten_value(value) }
    end
  end
  puts "CSV file #{csv_file} has been generated successfully!"
end

# Converting JSON files for all 4 tables

# 1. Medecin_By_Speciality
json_to_csv('Medecin_By_Speciality.json', 'Medecin_By_Speciality.csv')

# 2. Patient_By_BirthDay
json_to_csv('Patient_By_BirthDay.json', 'Patient_By_BirthDay.csv')

# 3. RendezVous_By_Date
json_to_csv('RendezVous_By_Date.json', 'RendezVous_By_Date.csv')

# 4. Consultation_By_Date
json_to_csv('Consultation_By_Date.json', 'Consultation_By_Date.csv')
