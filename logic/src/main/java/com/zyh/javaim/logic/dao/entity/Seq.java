package com.zyh.javaim.logic.dao.entity;


public class Seq {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_seq.id
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_seq.seq_id
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    private Long seqId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column im_seq.max_seq
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    private Long maxSeq;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_seq.id
     *
     * @return the value of im_seq.id
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_seq.id
     *
     * @param id the value for im_seq.id
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_seq.seq_id
     *
     * @return the value of im_seq.seq_id
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    public Long getSeqId() {
        return seqId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_seq.seq_id
     *
     * @param seqId the value for im_seq.seq_id
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column im_seq.max_seq
     *
     * @return the value of im_seq.max_seq
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    public Long getMaxSeq() {
        return maxSeq;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column im_seq.max_seq
     *
     * @param maxSeq the value for im_seq.max_seq
     *
     * @mbg.generated Tue Mar 26 22:04:51 CST 2024
     */
    public void setMaxSeq(Long maxSeq) {
        this.maxSeq = maxSeq;
    }
}