import { useState, useEffect, useCallback } from "react";
import axios from "axios";

const useJobs = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  // Fetch jobs from API
  const fetchJobs = useCallback(async () => {
    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("No token found");
        return;
      }
      const response = await axios.get("http://localhost:8080/api/jobs/all", {
        headers: { Authorization: `Bearer ${token}` },
      });
      const sortedJobs = response.data.sort((a, b) => b.id - a.id);
      setJobs(sortedJobs);
    } catch (error) {
      console.error("Error fetching jobs:", error);
    } finally {
      setLoading(false);
    }
  }, []);

  // Update a job
  const updateJob = async (jobId, formData) => {
    const token = localStorage.getItem("token");
    try {
      await axios.put(`http://localhost:8080/api/jobs/update/${jobId}`, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      });
      // Optionally, update the jobs list locally
      setJobs((prevJobs) =>
        prevJobs.map((job) => (job.id === jobId ? { ...job, ...formData } : job))
      );
    } catch (error) {
      throw error;
    }
  };

  // Delete a job
  const deleteJob = async (jobId) => {
    const token = localStorage.getItem("token");
    try {
      await axios.delete(`http://localhost:8080/api/jobs/delete/${jobId}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setJobs((prevJobs) => prevJobs.filter((job) => job.id !== jobId));
    } catch (error) {
      throw error;
    }
  };

  useEffect(() => {
    fetchJobs();
  }, [fetchJobs]);

  return { jobs, loading, fetchJobs, updateJob, deleteJob };
};

export default useJobs;