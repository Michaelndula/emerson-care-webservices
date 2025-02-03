import React, { useState, useEffect } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import { useTheme } from '@mui/material/styles';
import Grid from '@mui/material/Grid';
import MenuItem from '@mui/material/MenuItem';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import Chart from 'react-apexcharts';

// project-specific imports
import MainCard from 'ui-component/cards/MainCard';
import SkeletonTotalGrowthBarChart from 'ui-component/cards/Skeleton/TotalGrowthBarChart';
import { gridSpacing } from 'store/constant';

const status = [
  { value: 'today', label: 'Today' },
  { value: 'week', label: 'This Week' },
  { value: 'month', label: 'This Month' },
  { value: 'year', label: 'This Year' }
];

const initialChartOptions = {
  chart: { id: 'bar-chart', stacked: true, toolbar: { show: true }, zoom: { enabled: true } },
  responsive: [{ breakpoint: 480, options: { legend: { position: 'bottom' } } }],
  plotOptions: { bar: { horizontal: false, columnWidth: '50%' } },
  xaxis: { type: 'category', categories: [] },
  legend: { show: true, fontFamily: `'Roboto', sans-serif`, position: 'bottom' },
  fill: { type: 'solid' },
  dataLabels: { enabled: false },
  grid: { show: true },
  tooltip: { theme: 'light' }
};

const initialChartSeries = [{ name: 'Registered', data: [] }];

// Helper function to determine the relevant date
const getRelevantDate = (dateCreated, dateUpdated) => {
  const created = new Date(dateCreated);
  const updated = new Date(dateUpdated);
  return updated > created ? updated : created;
};

const TotalGrowthBarChart = ({ isLoading }) => {
  const [filter, setFilter] = useState('today');
  const [chartOptions, setChartOptions] = useState(initialChartOptions);
  const [chartSeries, setChartSeries] = useState(initialChartSeries);
  const theme = useTheme();

  const { primary } = theme.palette.text;
  const divider = theme.palette.divider;
  const grey500 = theme.palette.grey[500];
  const primary200 = theme.palette.primary[200];
  const primaryDark = theme.palette.primary.dark;
  const secondaryMain = theme.palette.secondary.main;
  const secondaryLight = theme.palette.secondary.light;

  // Apply theme updates to chart
  useEffect(() => {
    setChartOptions((prev) => ({
      ...prev,
      colors: [primary200, primaryDark, secondaryMain, secondaryLight],
      xaxis: { ...prev.xaxis, labels: { style: { colors: prev.xaxis.categories.map(() => primary) } } },
      yaxis: { labels: { style: { colors: [primary] } } },
      grid: { borderColor: divider },
      legend: { labels: { colors: grey500 } }
    }));
  }, [primary200, primaryDark, secondaryMain, secondaryLight, primary, divider, grey500]);

  // Fetch and process data
  useEffect(() => {
    async function fetchData() {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('/api/users/all', {
          headers: { Authorization: `Bearer ${token}` }
        });

        const users = response.data;
        const now = new Date();
        let categories = [];
        let registeredCounts = [];

        if (filter === 'today') {
          categories = Array.from({ length: 24 }, (_, i) => `${i}:00`);
          registeredCounts = new Array(24).fill(0);

          users.forEach((user) => {
            const relevantDate = getRelevantDate(user.dateCreated, user.dateUpdated);
            if (
              relevantDate.getFullYear() === now.getFullYear() &&
              relevantDate.getMonth() === now.getMonth() &&
              relevantDate.getDate() === now.getDate()
            ) {
              registeredCounts[relevantDate.getHours()] += 1;
            }
          });
        } else if (filter === 'week') {
          categories = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];
          registeredCounts = new Array(7).fill(0);

          // Get Monday of the current week
          const startOfWeek = new Date(now);
          startOfWeek.setDate(now.getDate() - ((now.getDay() + 6) % 7));
          startOfWeek.setHours(0, 0, 0, 0);

          // Get Sunday (End of the Week)
          const endOfWeek = new Date(startOfWeek);
          endOfWeek.setDate(startOfWeek.getDate() + 6);
          endOfWeek.setHours(23, 59, 59, 999);

          users.forEach((user) => {
            const relevantDate = getRelevantDate(user.dateCreated, user.dateUpdated);
            if (relevantDate >= startOfWeek && relevantDate <= endOfWeek) {
              const dayIndex = (relevantDate.getDay() + 6) % 7; // Monday = 0, Sunday = 6
              registeredCounts[dayIndex] += 1;
            }
          });
        } else if (filter === 'month') {
          categories = ['Week 1', 'Week 2', 'Week 3', 'Week 4'];
          registeredCounts = new Array(4).fill(0);

          users.forEach((user) => {
            const relevantDate = getRelevantDate(user.dateCreated, user.dateUpdated);
            if (relevantDate.getMonth() === now.getMonth() && relevantDate.getFullYear() === now.getFullYear()) {
              const weekIndex = Math.floor((relevantDate.getDate() - 1) / 7);
              registeredCounts[weekIndex] += 1;
            }
          });
        } else if (filter === 'year') {
          categories = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
          registeredCounts = new Array(12).fill(0);

          users.forEach((user) => {
            const relevantDate = getRelevantDate(user.dateCreated, user.dateUpdated);
            if (relevantDate.getFullYear() === now.getFullYear()) {
              registeredCounts[relevantDate.getMonth()] += 1;
            }
          });
        }

        setChartOptions((prev) => ({
          ...prev,
          xaxis: { ...prev.xaxis, categories }
        }));

        setChartSeries([{ name: 'Registered', data: registeredCounts }]);
      } catch (error) {
        console.error('Error fetching or processing chart data:', error);
      }
    }

    fetchData();
  }, [filter]);

  return (
    <>
      {isLoading ? (
        <SkeletonTotalGrowthBarChart />
      ) : (
        <MainCard>
          <Grid container spacing={gridSpacing}>
            <Grid item xs={12}>
              <Grid container alignItems="center" justifyContent="space-between">
                <Grid item>
                  <Typography variant="subtitle2">Total Registered Users</Typography>
                </Grid>
                <Grid item>
                  <TextField id="filter-select" select value={filter} onChange={(e) => setFilter(e.target.value)}>
                    {status.map((option) => (
                      <MenuItem key={option.value} value={option.value}>
                        {option.label}
                      </MenuItem>
                    ))}
                  </TextField>
                </Grid>
              </Grid>
            </Grid>
            <Grid item xs={12}>
              <Chart options={chartOptions} series={chartSeries} type="bar" height={480} />
            </Grid>
          </Grid>
        </MainCard>
      )}
    </>
  );
};

TotalGrowthBarChart.propTypes = { isLoading: PropTypes.bool };

export default TotalGrowthBarChart;