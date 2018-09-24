# onpremiig

--dropdown filter query
select distinct feed_id from logger_stats_master order by feed_id;

--Graph population query
select * from (select st.feed_id, st.batch_date, st.run_id, st.value as start_time, en.value as end_time, cast(timediff(en.value,st.value) as char) as difference from (select * from logger_stats_master where field='start') st inner join (select * from logger_stats_master where field='end') en on st.feed_id=en.feed_id and st.run_id=en.run_id and st.batch_date=en.batch_date)a  where feed_id is not null order by batch_date;