import React, { useEffect, useState } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';

// Material-UI components
import { Card, Grid, Typography, Avatar, Divider, Button, CardActions, CardContent } from '@mui/material';
import { KeyboardArrowUpOutlined, KeyboardArrowDownOutlined, ChevronRightOutlined } from '@mui/icons-material';

// Custom components
import JobsAreaCard from './JobsAreaCard';
import MainCard from 'ui-component/cards/MainCard';
import SkeletonPopularCard from 'ui-component/cards/Skeleton/PopularCard';
import { gridSpacing } from 'store/constant';

const PopularCard = ({ isLoading }) => {
  const [jobs, setJobs] = useState([]);

  // Fetch jobs from API
  useEffect(() => {
    async function fetchJobs() {
      try {
        const token = localStorage.getItem('token');
        const response = await axios.get('/api/jobs/all', {
          headers: { Authorization: `Bearer ${token}` }
        });

        const sortedJobs = response.data
          .sort((a, b) => b.id - a.id)
          .slice(0, 4);
        setJobs(sortedJobs);
      } catch (error) {
        console.error('Error fetching jobs:', error);
      }
    }

    fetchJobs();
  }, []);

  return (
      <>
        {isLoading ? (
          <SkeletonPopularCard />
        ) : (
          <MainCard content={false}>
            <CardContent>
              <Grid container spacing={gridSpacing}>
                <Grid item xs={12}>
                  <Typography variant="h4">Recent Jobs</Typography>
                </Grid>
                <Grid item xs={12} sx={{ pt: '16px !important' }}>
                    <JobsAreaCard />
                </Grid>
                {jobs.length === 0 ? (
                  <Grid item xs={12} sx={{ textAlign: 'center', py: 3 }}>
                    <Typography variant="h6" color="textSecondary">
                      No Jobs Posted Yet
                    </Typography>
                  </Grid>
                ) : (
                  jobs.map((job) => (
                    <Grid item xs={12} key={job.id}>
                      <Grid container direction="column">
                        <Grid item>
                          <Grid container alignItems="center" justifyContent="space-between">
                            <Grid item>
                              <Typography variant="subtitle1" color="inherit">
                                {job.title}
                              </Typography>
                            </Grid>
                            <Grid item>
                              <Grid container alignItems="center" justifyContent="space-between">
                                <Grid item>
                                  <Typography variant="subtitle1" color="inherit">
                                    {job.rate}
                                  </Typography>
                                </Grid>
                                <Grid item>
                                  <Avatar
                                    variant="rounded"
                                    sx={{
                                      width: 16,
                                      height: 16,
                                      borderRadius: '5px',
                                      bgcolor: job.applicationCount > 5 ? 'success.light' : 'orange.light',
                                      color: job.applicationCount > 5 ? 'success.dark' : 'orange.dark',
                                      ml: 2
                                    }}
                                  >
                                    {job.applicationCount > 5 ? (
                                      <KeyboardArrowUpOutlined fontSize="small" color="inherit" />
                                    ) : (
                                      <KeyboardArrowDownOutlined fontSize="small" color="inherit" />
                                    )}
                                  </Avatar>
                                </Grid>
                              </Grid>
                            </Grid>
                          </Grid>
                        </Grid>
                        <Grid item>
                          <Typography variant="subtitle2" sx={{ color: job.applicationCount > 5 ? 'success.dark' : 'orange.dark' }}>
                            {job.applicationCount > 5 ? 'High Applications' : 'Low Applications'}
                          </Typography>
                        </Grid>
                      </Grid>
                      <Divider sx={{ my: 1.5 }} />
                    </Grid>
                  ))
                )}
              </Grid>
            </CardContent>
            <CardActions sx={{ p: 1.25, pt: 0, justifyContent: 'center' }}>
              <Button size="small" disableElevation>
                View All
                <ChevronRightOutlined />
              </Button>
            </CardActions>
          </MainCard>
        )}
      </>
    );
  };

  PopularCard.propTypes = {
    isLoading: PropTypes.bool
  };

  export default PopularCard;