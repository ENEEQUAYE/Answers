@echo off
echo Testing immediate save functionality...
echo.

echo Adding a test vehicle (DEF456)...
echo 1> temp_input.txt
echo 1>> temp_input.txt
echo DEF456>> temp_input.txt
echo Van>> temp_input.txt
echo 75000>> temp_input.txt
echo 10.2>> temp_input.txt
echo 4>> temp_input.txt
echo 6>> temp_input.txt

java AdomLogisticsSystem < temp_input.txt

echo.
echo Checking vehicles.txt file:
type vehicles.txt

del temp_input.txt
pause
