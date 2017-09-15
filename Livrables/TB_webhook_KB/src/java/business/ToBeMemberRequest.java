package business;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author boris.klett
 */
public class ToBeMemberRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private Integer id;

    private Date requestDate;

    private Date traitmentDate;

    private Student student;

    public ToBeMemberRequest() {
    }

    public ToBeMemberRequest(Integer id, Date requestDate, Date traitmentDate, Student student) {
        this.id = id;
        this.requestDate = requestDate;
        this.traitmentDate = traitmentDate;
        this.student = student;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getTraitmentDate() {
        return traitmentDate;
    }

    public void setTraitmentDate(Date traitmentDate) {
        this.traitmentDate = traitmentDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    
    
}
